import 'package:flutter/material.dart';
import 'package:flutter_bubbles/models/person.dart';

class PersonTile extends StatelessWidget {
  final Person person;
  final Function()? onTap;
  const PersonTile({super.key, required this.person, required this.onTap});

  @override
  Widget build(BuildContext context) {
    final String birthday =
        person.birthday == null ? "no" : person.birthday.toString();
    return GestureDetector(
        onTap: onTap,
        child: ListTile(
          title: Text(person.name),
          subtitle: Text(birthday),
          leading: const Icon(Icons.person),
        ));
  }
}
