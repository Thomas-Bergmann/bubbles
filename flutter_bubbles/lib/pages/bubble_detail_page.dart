import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:flutter_bubbles/models/bubble.dart';
import 'package:flutter_bubbles/models/bubble_dao.dart';
import 'package:flutter_bubbles/widgets/app_bar.dart';

class BubbleDetailPage extends StatelessWidget {
  final Bubble bubble;
  final bool isNew;
  const BubbleDetailPage(
      {super.key, required this.bubble, required this.isNew});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: createSimpleAppBar(context, bubble.name),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: _getHumansBody(context),
      ),
    );
  }

  // https://www.youtube.com/watch?v=RPvhoghXn54 minute 10
  Widget _getHumansBody(BuildContext context) {
    var controllerForName = TextEditingController(text: bubble.name);
    final formKey = GlobalKey<FormState>();
    return Material(
        child: Form(
            key: formKey,
            child: Column(children: [
              TextFormField(
                  decoration: const InputDecoration(
                      labelText: 'Name*', hintText: 'Name of bubble'),
                  controller: controllerForName,
                  keyboardType: TextInputType.name),
              _getButtons(context, controllerForName, formKey),
            ])));
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
    BubbleDao model = Provider.of<BubbleDao>(context, listen: false);
    model.delete(bubble);
    // go back to list
    Navigator.pop(context);
  }

  void onSave(BuildContext context, TextEditingController controllerForName,
      GlobalKey<FormState> formKey) {
    // update bubble and inform model
    formKey.currentState?.save();
    bubble.name = controllerForName.text;
    BubbleDao model = Provider.of<BubbleDao>(context, listen: false);
    if (isNew) {
      model.insert(bubble);
    } else {
      model.update(bubble);
    }
    // go back to list
    Navigator.pop(context);
  }
}
