import 'package:flutter/material.dart';
import 'package:flutter_bubbles/database/remote/firestore_person.dart';
import 'package:flutter_bubbles/database/remote/firestore_user.dart';

import 'package:flutter_bubbles/models/person.dart';
import 'package:uuid/uuid.dart';

class PersonModel extends ChangeNotifier {
  final String uuid = const Uuid().v4();
  FireStorePerson? _fireStorePerson;
  List<Person> _persons = [];
  int _lastCount = 0;
  PersonModel() {
    print("PersonModel($uuid): created ");
  }

  List<Person> getPersons() {
    print("PersonModel($uuid): get persons called.");
    if (_fireStorePerson != null && _lastCount == 0 && _persons.isEmpty) {
      _persons = _fireStorePerson?.getAll() ?? [];
      print(
          "PersonModel($uuid): retrieved persons from db: ${_persons.length} ($_lastCount)");
    }
    print("PersonModel($uuid): get persons called: ${_persons.length} ($_lastCount)");
    return _persons;
  }

  void addPerson(Person person) {
    _fireStorePerson?.add(person);
    _persons.add(person);
    notifyListeners();
  }

  void savePerson(Person person) {
    _fireStorePerson?.save(person);
    notifyListeners();
  }

  void deletePerson(Person person) {
    _fireStorePerson?.delete(person);
    _persons.remove(person);
    notifyListeners();
  }

  void retrieveUpdate() {
    if (_lastCount != _persons.length) {
      print(
          "PersonModel($uuid): retrieveUpdate REAL updated db: ${_persons.length} was $_lastCount");
      _lastCount = _persons.length;
      notifyListeners();
    } else {
      print(
          "PersonModel($uuid): retrieveUpdate NOT updated db: ${_persons.length}");
    }
  }

  void setUser(FireStoreUser fireStoreUser) {
    var fireStorePerson = FireStorePerson(user: fireStoreUser);
    fireStorePerson.addListener(() => retrieveUpdate());
    _fireStorePerson = fireStorePerson;
    var persons = getPersons();
    print(
        "PersonModel($uuid): updated user (${fireStoreUser.userid}): ${persons.length}");
  }
}
