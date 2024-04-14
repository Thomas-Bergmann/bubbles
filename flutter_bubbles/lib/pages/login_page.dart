import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bubbles/database/remote/firestore_user.dart';
import 'package:flutter_bubbles/models/auth_model.dart';
import 'package:flutter_bubbles/models/person_dao.dart';
import 'package:provider/provider.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});
  @override
  State<StatefulWidget> createState() => LoginPageState();
}

class LoginPageState extends State<LoginPage> {
  bool _isExternalLoginError = false;
  FirebaseAuthException? _externalLoginError;
  final controllerForLogin = TextEditingController();
  final controllerForPassword = TextEditingController();

  @override
  Widget build(BuildContext context) {
    whatOnLoginChanges(context);
    return Scaffold(
        appBar: AppBar(
          title: const Text('Bubbles 1.0.0'),
          centerTitle: true,
        ),
        body: Padding(
          padding: const EdgeInsets.all(16),
          child: _getLoginBody(context),
        ),
        floatingActionButton: FloatingActionButton(
          // When the button is pressed,
          // give focus to the text field using myFocusNode.
          onPressed: () =>
              onLogin(context, controllerForLogin, controllerForPassword),
          tooltip: 'Login',
          child: const Icon(Icons.login),
        ));
  }

  Widget _getLoginBody(BuildContext context) {
    return Column(children: [
      Text(getInfoText()),
      Form(
        autovalidateMode: AutovalidateMode.always,
        child: TextFormField(
          decoration: const InputDecoration(hintText: 'Email'),
          controller: controllerForLogin,
          keyboardType: TextInputType.emailAddress,
          validator: (value) => getEmailMessage(value),
        ),
      ),
      Form(
        autovalidateMode: AutovalidateMode.always,
        child: TextFormField(
          decoration: const InputDecoration(hintText: 'Password'),
          controller: controllerForPassword,
          keyboardType: TextInputType.visiblePassword,
          validator: (value) => (value != null ? isPassword(value) : true)
              ? null
              : "Please enter a valid password.",
        ),
      ),
    ]);
  }

  String getInfoText() {
    if (_isExternalLoginError) {
      return 'Login failed: $_externalLoginError.';
    }
    return 'Pleaser enter email and password and press \'Login\'.';
  }

  String? getEmailMessage(String? value) {
    if (value == null || value.trim().isEmpty) {
      return null; // is empty
    }
    if (_isExternalLoginError) {
      return 'Login failed: $_externalLoginError.';
    }
    if (value.contains('@')) {
      return null; // not validated yet
    }
    return "Please enter a valid email address.";
  }

  onLogin(BuildContext context, TextEditingController forEMail,
      TextEditingController forPassword) {
    try {
      FirebaseAuth.instance
          .signInWithEmailAndPassword(
              email: forEMail.text, password: forPassword.text)
          .then((value) => Navigator.pop(context));
    } on FirebaseAuthException catch (error) {
      _isExternalLoginError = true;
      _externalLoginError = error;
      print('login failed $error');
      return true;
    }
  }

  bool isEmail(String value) {
    return value.contains("@");
  }

  bool isPassword(String value) {
    return value.length > 4;
  }

  void whatOnLoginChanges(BuildContext context) {
    FirebaseAuth.instance.authStateChanges().listen((User? user) {
      final authModel = Provider.of<AuthModel>(context, listen: false);
      if (user == null) {
        authModel.logout();
      } else {
        print('user successfully logged-in $user');
        authModel.login(user);
        Provider.of<PersonDao>(context, listen: false).setUser(FireStoreUser(userid: user.uid));
      }
    });
  }
}
