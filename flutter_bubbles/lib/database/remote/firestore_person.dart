import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bubbles/database/remote/firestore_user.dart';

import 'package:flutter_bubbles/models/person.dart';
import 'package:uuid/uuid.dart';

class FireStorePerson extends ChangeNotifier {
  // https://firebase.google.com/docs/firestore/query-data/queries?hl=de&authuser=0

  // https://console.firebase.google.com/project/bubbles-b12be/overview
  CollectionReference<Map<String, dynamic>> collection =
      FirebaseFirestore.instance.collection('people');

  final FireStoreUser user;
  final List<String> info = [];
  final List<String> errors = [];
  final String uuid = const Uuid().v4();
  final Map<String, Person> _data = {};

  FireStorePerson({required this.user}) {
    print("FireStorePerson($uuid): created ");
    var snapshots = collection
        .where(Person.fieldFirestoreUserID, isEqualTo: user.userid)
        .snapshots();
    snapshots.forEach((snapshot) => _addToResult(_data, snapshot)).catchError(
        (error) => print(
            "FireStorePerson($uuid): error loaded persons from db: ${error}"));
  }

  /// return the list of all person by current user
  Map<String, Person> getAll() {
    return _data;
  }

  void _addToResult(Map<String, Person> result, snapshot) {
    for (var doc in snapshot.docs) {
      var person = Person.fromFireStore(doc);
      result[doc.id.toString()] = person;
      print(
          "FireStorePerson($uuid): update person from db: ${doc.id} - name ${person.name} with ${person.birthday}");
    }
    notifyListeners();
  }

  /// Add remote person
  void add(Person person) {
    person.setFireStoreUser(user.userid);
    collection
        .add(person.toFireStore())
        .then((doc) => person.firestoreId = doc.id)
        // ignore: invalid_return_type_for_catch_error
        .catchError((error) => errors.add("Failed to create person: $error"));
  }

  /// Update remote person
  void save(Person person) {
    collection
        .doc(person.firestoreId)
        .set(person.toFireStore())
        .then((value) =>
            info.add("'full_name' & 'age' merged with existing data!"))
        .catchError((error) => errors.add("Failed to save: $error"));
  }

  void delete(Person person) {
    collection
        .doc(person.firestoreId)
        .delete()
        .then((value) => info.add("'Deleted person ${person.name}."))
        .catchError((error) => errors.add("Failed to delete: $error"));
  }
}
