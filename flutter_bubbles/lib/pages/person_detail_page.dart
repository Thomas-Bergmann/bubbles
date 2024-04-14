import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:flutter_bubbles/models/person.dart';
import 'package:flutter_bubbles/models/person_dao.dart';
import 'package:flutter_bubbles/widgets/app_bar.dart';

class PersonDetailPage extends StatelessWidget {
  final Person person;
  final bool isNew;
  const PersonDetailPage(
      {super.key, required this.person, required this.isNew});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: createSimpleAppBar(context, person.name),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: _getHumansBody(context),
      ),
    );
  }

  // https://www.youtube.com/watch?v=RPvhoghXn54 minute 10
  Widget _getHumansBody(BuildContext context) {
    var controllerForName = TextEditingController(text: person.name);
    final formKey = GlobalKey<FormState>();
    return Material(
        child: Form(
            key: formKey,
            child: Column(children: [
              TextFormField(
                  decoration: const InputDecoration(
                      labelText: 'Name*', hintText: 'Full name of person'),
                  controller: controllerForName,
                  keyboardType: TextInputType.name),
              InputDatePickerFormField(
                fieldLabelText: 'Birthday',
                firstDate: DateTime.fromMicrosecondsSinceEpoch(0),
                lastDate: DateTime.now(),
                initialDate: person.birthday,
                onDateSubmitted: onDateTimeChange,
                onDateSaved: onDateTimeChange,
              ),
              _getButtons(context, controllerForName, formKey),
            ])));
  }
  void onDateTimeChange(DateTime dateTime)
  {
    print('date changed: ${dateTime}');
    person.birthday = dateTime;
  }
  Widget _getButtons(BuildContext context,
      TextEditingController controllerForName, GlobalKey<FormState> formKey) {
    var buttons = [
      IconButton(
        icon: const Icon(Icons.save),
        onPressed: () => onSave(context, controllerForName, formKey),
        tooltip: "Save",
      )
    ];
    if (!isNew) {
      buttons.add(IconButton(
        icon: const Icon(Icons.delete),
        onPressed: () => onDelete(context),
        tooltip: "Delete",
      ));
    }
    buttons.add(
      IconButton(
          icon: const Icon(Icons.cancel),
          tooltip: "Cancel",
          onPressed: () => Navigator.pop(context)),
    );
    return Row(children: buttons);
  }

  void onDelete(BuildContext context) {
    PersonDao model = Provider.of<PersonDao>(context, listen: false);
    model.delete(person);
    // go back to list
    Navigator.pop(context);
  }

  void onSave(BuildContext context, TextEditingController controllerForName,
      GlobalKey<FormState> formKey) {
    // update person and inform model
    formKey.currentState?.save();
    person.name = controllerForName.text;
    print("update person ${person.name} with birthday ${person.birthday} state ${formKey.currentState}");
    PersonDao model = Provider.of<PersonDao>(context, listen: false);
    if (isNew) {
      model.insert(person);
    } else {
      model.update(person);
    }
    // go back to list
    Navigator.pop(context);
  }
}
