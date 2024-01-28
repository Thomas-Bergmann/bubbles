import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_bubbles/models/auth_model.dart';
import 'package:flutter_bubbles/models/person_model.dart';
import 'package:flutter_bubbles/pages/humans_page.dart';
import 'package:flutter_bubbles/pages/login_page.dart';
import 'package:flutter_bubbles/pages/preference_page.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Consumer2<AuthModel, PersonModel>(
        builder: (context, authModel, personModel, child) => Scaffold(
            appBar: AppBar(
              title: const Text('Bubbles 1.0.0'),
              centerTitle: true,
            ),
            body: _getHomePageBody(context, authModel, personModel),
            floatingActionButton: !authModel.isLoggedIn()
                ? null
                : FloatingActionButton(
                    tooltip: 'Logout',
                    onPressed: () => authModel.logout(),
                    child: const Icon(Icons.logout_rounded),
                  )));
  }

  Widget _getHomePageBody(
      BuildContext context, AuthModel authModel, PersonModel personModel) {
    List<Widget> children = List.empty(growable: true);
    if (authModel.isLoggedIn()) {
      children
          .add(_getButton(context, getText(personModel), const HumansPage()));
    } else {
      children.add(_getButton(context, "Login", const LoginPage()));
    }
    children.add(_getButton(context, "Preferences", const PreferencesPage()));
    return Column(children: children);
  }

  String getText(PersonModel personModel) {
    var persons = personModel.getPersons();
    print('HomePage: persons ${persons.length} @ ${personModel.uuid}');
    return 'Persons (${persons.length} @ ${personModel.uuid})';
  }

  Center _getButton(BuildContext context, String text, Widget page) {
    return Center(
      child: ElevatedButton(
          child: Text(text),
          onPressed: () => {
                Navigator.push(
                    context, MaterialPageRoute(builder: (context) => page))
              }),
    );
  }
}
