import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/person.dart';
import 'package:flutter_bubbles/widgets/text_input_field.dart';
import 'package:provider/provider.dart';

import 'package:flutter_bubbles/models/person_model.dart';
import 'package:flutter_bubbles/widgets/app_bar.dart';

class HumansDetailPage extends StatelessWidget {
  final Person person;
  final bool isNew;
  const HumansDetailPage(
      {super.key, required this.person, required this.isNew});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: createSimpleAppBar(context, person.name),
      body: getHumansBody(context),
    );
  }

  // https://www.youtube.com/watch?v=RPvhoghXn54 minute 10
  Column getHumansBody(BuildContext context) {
    var controllerForName = TextEditingController(text: person.name);

    return Column(children: [
      TextInputField(
          controller: controllerForName, keyboardType: TextInputType.name),
      getButtons(context, controllerForName),
    ]);
  }

  Widget getButtons(BuildContext context, TextEditingController controllerForName) {
    var buttons = [
      IconButton(
        icon: const Icon(Icons.save),
        onPressed: () => onSave(context, controllerForName),
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
    PersonModel model = Provider.of<PersonModel>(context, listen: false);
    model.deletePerson(person);
    // go back to list
    Navigator.pop(context);
  }

  void onSave(BuildContext context, TextEditingController controllerForName) {
    // update person and inform model
    person.name = controllerForName.text;
    PersonModel model = Provider.of<PersonModel>(context, listen: false);
    if (isNew) {
      model.addPerson(person);
    } else {
      model.savePerson(person);
    }
    // go back to list
    Navigator.pop(context);
  }
}
