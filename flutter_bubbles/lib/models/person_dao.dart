import 'package:flutter/material.dart';
import 'package:flutter_bubbles/database/remote/firestore_person.dart';
import 'package:flutter_bubbles/database/remote/firestore_user.dart';

import 'package:flutter_bubbles/models/person.dart';
import 'package:uuid/uuid.dart';

class PersonDao extends ChangeNotifier {
  final String uuid = const Uuid().v4();
  FireStorePerson? _fireStorePerson;
  Map<String, Person> _data = {};
  PersonDao() {
    print("PersonDao($uuid): created ");
  }

  List<Person> getAll() {
    return _data.values.toList();
  }

  void insert(Person person) {
    // will add person to model via snapshots
    _fireStorePerson?.add(person);
    notifyListeners();
  }

  void update(Person person) {
    _fireStorePerson?.save(person);
    notifyListeners();
  }

  void delete(Person person) {
    _fireStorePerson?.delete(person);
    _data.remove(person.firestoreId);
    notifyListeners();
  }

  void setUser(FireStoreUser fireStoreUser) {
    if (_fireStorePerson == null)
    {
      var fireStorePerson = FireStorePerson(user: fireStoreUser);
      fireStorePerson.addListener(notifyListeners);
      _fireStorePerson = fireStorePerson;
      _data = fireStorePerson.getAll();
      print(
          "PersonDao($uuid): updated authentication (${fireStoreUser.userid}): ${_data.length}");
    }
  }
}
