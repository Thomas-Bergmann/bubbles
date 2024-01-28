import 'package:flutter/material.dart';

AppBar createSimpleAppBar(BuildContext context, String text) {
  return AppBar(
    title: Text(text),
    centerTitle: true,
  );
}

AppBar createAppBarWithAdd(BuildContext context, String text, Function() addFunction) {
  return AppBar(
    title: Text(text),
    centerTitle: true,
    actions: [
      ElevatedButton(
        onPressed: addFunction,
        child: const Text("+"),
      )
    ],
  );
}
