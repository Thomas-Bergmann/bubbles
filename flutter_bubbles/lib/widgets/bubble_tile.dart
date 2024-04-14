import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/bubble.dart';
import 'package:intl/intl.dart';

class BubbleTile extends StatelessWidget {
  final Bubble bubble;
  final Function()? onTap;
  final DateFormat dateFormat;
  const BubbleTile(
      {super.key,
      required this.bubble,
      required this.onTap,
      required this.dateFormat});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
        onTap: onTap,
        child: ListTile(
          title: Text(bubble.name),
          leading: const Icon(Icons.add_circle),
        ));
  }
}
