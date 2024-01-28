import 'package:flutter/material.dart';
import 'package:flutter_bubbles/database/remote/firestore_bubble.dart';
import 'package:flutter_bubbles/database/remote/firestore_user.dart';

import 'package:flutter_bubbles/models/bubble.dart';
import 'package:uuid/uuid.dart';

class BubbleDao extends ChangeNotifier {
  final String uuid = const Uuid().v4();
  FireStoreBubble? _fireStoreBubble;
  Map<String, Bubble> _data = {};
  BubbleDao() {
    print("BubbleDao($uuid): created ");
  }

  List<Bubble> getAll() {
    return _data.values.toList();
  }

  void insert(Bubble bubble) {
    // will add bubble to model via snapshots
    _fireStoreBubble?.add(bubble);
    notifyListeners();
  }

  void update(Bubble bubble) {
    _fireStoreBubble?.save(bubble);
    notifyListeners();
  }

  void delete(Bubble bubble) {
    _fireStoreBubble?.delete(bubble);
    _data.remove(bubble.firestoreId);
    notifyListeners();
  }

  void setUser(FireStoreUser fireStoreUser) {
    if (_fireStoreBubble == null)
    {
      var firestore = FireStoreBubble(user: fireStoreUser);
      firestore.addListener(notifyListeners);
      _fireStoreBubble = firestore;
      _data = firestore.getAll();
      print(
          "PersonDao($uuid): updated authentication (${fireStoreUser.userid}): ${_data.length}");
    }
  }
}
