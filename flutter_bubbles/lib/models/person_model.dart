import 'package:flutter/material.dart';
import 'package:flutter_bubbles/database/remote/firestore_person.dart';
import 'package:flutter_bubbles/database/remote/firestore_user.dart';

import 'package:flutter_bubbles/models/person.dart';
import 'package:uuid/uuid.dart';

class PersonModel extends ChangeNotifier {
  final String uuid = const Uuid().v4();
  FireStorePerson? _fireStorePerson;
  Map<String, Person> _persons = {};
  PersonModel() {
    print("PersonModel($uuid): created ");
  }

  List<Person> getPersons() {
    if (_fireStorePerson != null && _persons.isEmpty) {
      print("PersonModel($uuid): get persons called.");
      _persons = _fireStorePerson?.getAll() ?? {};
    }
    return _persons.values.toList();
  }

  void addPerson(Person person) {
    // will add person to model via snapshots
    _fireStorePerson?.add(person);
    notifyListeners();
  }

  void savePerson(Person person) {
    _fireStorePerson?.save(person);
    notifyListeners();
  }

  void deletePerson(Person person) {
    _fireStorePerson?.delete(person);
    _persons.remove(person.firestoreId);
    notifyListeners();
  }

  void setUser(FireStoreUser fireStoreUser) {
    var fireStorePerson = FireStorePerson(user: fireStoreUser);
    fireStorePerson.addListener(notifyListeners);
    _fireStorePerson = fireStorePerson;
    print(
        "PersonModel($uuid): updated authentication (${fireStoreUser.userid}): ${_persons.length}");
  }
}
