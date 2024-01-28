import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/preference_model.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import 'package:flutter_bubbles/models/person.dart';
import 'package:flutter_bubbles/models/person_dao.dart';
import 'package:flutter_bubbles/pages/person_detail_page.dart';
import 'package:flutter_bubbles/widgets/app_bar.dart';
import 'package:flutter_bubbles/widgets/person_tile.dart';

class PeoplePage extends StatelessWidget {
  const PeoplePage({super.key});

  @override
  build(BuildContext context) {
    return Consumer2<PersonDao, PreferenceModel>(
        builder: (context, personDao, preferenceMode, child) => Scaffold(
              appBar: createAppBarWithAdd(
                  context,
                  'People (${personDao.getAll().length})',
                  () => _navigateToAddPerson(context)),
              body: _getPeopleBody(context, personDao, preferenceMode),
            ));
  }

  // https://www.youtube.com/watch?v=RPvhoghXn54 minute 10
  Widget _getPeopleBody(BuildContext context, PersonDao personDao,
      PreferenceModel preferenceModel) {
    final persons = personDao.getAll();
    print('PeoplePage: persons ${persons.length} @ ${personDao.uuid}');
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
                PersonDetailPage(person: person, isNew: false)));
  }

  void _navigateToAddPerson(BuildContext context) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) =>
                PersonDetailPage(person: Person(), isNew: true)));
  }
}
