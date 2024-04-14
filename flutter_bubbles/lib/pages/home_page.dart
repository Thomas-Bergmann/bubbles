import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/bubble_dao.dart';
import 'package:flutter_bubbles/pages/bubbles_page.dart';
import 'package:provider/provider.dart';
import 'package:flutter_bubbles/models/auth_model.dart';
import 'package:flutter_bubbles/models/person_dao.dart';
import 'package:flutter_bubbles/pages/people_page.dart';
import 'package:flutter_bubbles/pages/login_page.dart';
import 'package:flutter_bubbles/pages/preference_page.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Consumer3<AuthModel, PersonDao, BubbleDao>(
        builder: (context, authModel, personDao, bubbleDao, child) => Scaffold(
            appBar: AppBar(
              title: const Text('Bubbles 1.0.0'),
              centerTitle: true,
            ),
            body: _getHomePageBody(context, authModel, personDao, bubbleDao),
            floatingActionButton: !authModel.isLoggedIn()
                ? null
                : FloatingActionButton(
                    tooltip: 'Logout',
                    onPressed: () => authModel.logout(),
                    child: const Icon(Icons.logout_rounded),
                  )));
  }

  Widget _getHomePageBody(
      BuildContext context, AuthModel authModel, PersonDao personDao, BubbleDao bubbleDao) {
    List<Widget> children = List.empty(growable: true);
    if (authModel.isLoggedIn()) {
      children
          .add(_getButton(context, getTextPeople(personDao), const PeoplePage()));
      children
          .add(_getButton(context, getTextBubbles(bubbleDao), const BubblesPage()));
    } else {
      children.add(_getButton(context, "Login", const LoginPage()));
    }
    children.add(_getButton(context, "Preferences", const PreferencesPage()));
    return Column(children: children);
  }

  String getTextPeople(PersonDao personDao) {
    var persons = personDao.getAll();
    print('HomePage: persons ${persons.length} @ ${personDao.uuid}');
    return 'People (${persons.length})';
  }
  String getTextBubbles(BubbleDao dao) {
    var bubbles = dao.getAll();
    print('HomePage: bubbles ${bubbles.length} @ ${dao.uuid}');
    return 'Bubbles (${bubbles.length})';
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
