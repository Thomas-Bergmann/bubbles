import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/person.dart';
import 'package:intl/intl.dart';

class PersonTile extends StatelessWidget {
  final Person person;
  final Function()? onTap;
  final DateFormat dateFormat;
  const PersonTile({super.key, required this.person, required this.onTap, required this.dateFormat});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
        onTap: onTap,
        child: ListTile(
          title: Text(person.name),
          subtitle: Text(person.birthday == null ? 'no' : dateFormat.format(person.birthday!)),
          leading: const Icon(Icons.person),
        ));
  }
}
