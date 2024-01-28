import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:flutter_bubbles/models/person.dart';
import 'package:flutter_bubbles/models/person_model.dart';
import 'package:flutter_bubbles/pages/humans_detail_page.dart';
import 'package:flutter_bubbles/widgets/app_bar.dart';
import 'package:flutter_bubbles/widgets/person_tile.dart';

class HumansPage extends StatelessWidget {
  const HumansPage({super.key});

  @override
  build(BuildContext context) {
    return Consumer<PersonModel>(
        builder: (context, model, child) => Scaffold(
              appBar: createAppBarWithAdd(
                  context, 'Persons (${model.getPersons().length} @ ${model.uuid})', () => _navigateToAddPerson(context)),
              body: _getHumansBody(context, model),
            ));
  }

  // https://www.youtube.com/watch?v=RPvhoghXn54 minute 10
  Widget _getHumansBody(BuildContext context, PersonModel personModel) {
    final persons = personModel.getPersons();
    print('HumansPage: persons ${persons.length} @ ${personModel.uuid}');
    return Column(children: [
      Expanded(
        child: ListView.builder(
            itemCount: persons.length,
            itemBuilder: (context, index) => PersonTile(
                onTap: () => _navigateToPerson(context, persons[index]),
                person: persons[index])),
      ),
    ]);
  }

  void _navigateToPerson(BuildContext context, Person person) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) =>
                HumansDetailPage(person: person, isNew: false)));
  }

  void _navigateToAddPerson(BuildContext context) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) =>
                HumansDetailPage(person: Person(), isNew: true)));
  }
}
