import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:uuid/uuid.dart';

class AuthModel extends ChangeNotifier {
  final String uuid = const Uuid().v4();
  AuthModel()
  {
    print("AuthModel created $uuid");
  }
  bool isLoggedIn() {
    return FirebaseAuth.instance.currentUser != null;
  }

  void logout() {
    print('logout user');
    FirebaseAuth.instance.signOut();
    notifyListeners();
  }

  void login(User user) {
    notifyListeners();
  }

  String? getUserID() {
    return FirebaseAuth.instance.currentUser?.uid;
  }
}
