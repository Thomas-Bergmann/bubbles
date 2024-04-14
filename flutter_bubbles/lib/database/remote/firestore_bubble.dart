import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bubbles/database/remote/firestore_user.dart';

import 'package:flutter_bubbles/models/bubble.dart';
import 'package:uuid/uuid.dart';

class FireStoreBubble extends ChangeNotifier {
  // https://firebase.google.com/docs/firestore/query-data/queries?hl=de&authuser=0

  // https://console.firebase.google.com/project/bubbles-b12be/overview
  CollectionReference<Map<String, dynamic>> collection =
      FirebaseFirestore.instance.collection('bubbles');

  final FireStoreUser user;
  final List<String> info = [];
  final List<String> errors = [];
  final String uuid = const Uuid().v4();
  final Map<String, Bubble> _data = {};

  FireStoreBubble({required this.user}) {
    print("FireStoreBubble($uuid): created ");
    var snapshots = collection
        .where(Bubble.fieldFirestoreUserID, isEqualTo: user.userid)
        .snapshots();
    snapshots.forEach((snapshot) => _addToResult(_data, snapshot)).catchError(
        (error) => print(
            "FireStoreBubble($uuid): error loaded bubbles from db: ${error}"));
  }

  Map<String, Bubble> getAll() {
    return _data;
  }

  void _addToResult(Map<String, Bubble> result, snapshot) {
    for (var doc in snapshot.docs) {
      var bubble = Bubble.fromFireStore(doc);
      result[doc.id.toString()] = bubble;
      print(
          "FireStoreBubble($uuid): update bubble from db: ${doc.id} - ${bubble.name}");
    }
    notifyListeners();
  }

  /// Add remote bubble
  void add(Bubble bubble) {
    bubble.setFireStoreUser(user.userid);
    collection
        .add(bubble.toFireStore())
        .then((doc) => bubble.firestoreId = doc.id)
        // ignore: invalid_return_type_for_catch_error
        .catchError((error) => errors.add("Failed to create bubble: $error"));
  }

  /// Update remote bubble
  void save(Bubble bubble) {
    collection
        .doc(bubble.firestoreId)
        .set(bubble.toFireStore())
        .then((value) =>
            info.add("'full_name' & 'age' merged with existing data!"))
        .catchError((error) => errors.add("Failed to save: $error"));
  }

  void delete(Bubble bubble) {
    collection
        .doc(bubble.firestoreId)
        .delete()
        .then((value) => info.add("'Deleted bubble ${bubble.name}."))
        .catchError((error) => errors.add("Failed to delete: $error"));
  }
}
