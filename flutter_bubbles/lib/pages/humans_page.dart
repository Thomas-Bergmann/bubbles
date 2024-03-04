import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/preference_model.dart';
import 'package:intl/intl.dart';
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
    return Consumer2<PersonModel, PreferenceModel>(
        builder: (context, personModel, preferenceMode, child) => Scaffold(
              appBar: createAppBarWithAdd(
                  context, 'People (${personModel.getPersons().length})', () => _navigateToAddPerson(context)),
              body: _getHumansBody(context, personModel, preferenceMode),
            ));
  }

  // https://www.youtube.com/watch?v=RPvhoghXn54 minute 10
  Widget _getHumansBody(BuildContext context, PersonModel personModel, PreferenceModel preferenceModel) {
    final persons = personModel.getPersons();
    print('HumansPage: persons ${persons.length} @ ${personModel.uuid}');
    return Column(children: [
      Expanded(
        child: ListView.builder(
            itemCount: persons.length,
            itemBuilder: (context, index) => PersonTile(
                dateFormat: DateFormat.yMMMd(preferenceModel.locale),
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
