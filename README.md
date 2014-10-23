portable-util
=============
This repository contains a number of useful utilities that can help you with your Hazelcast development. We aim to design the utilities in such as way they will fit into any architecture regardless of the technology choice.

####ReflectionClassDefinitionBuilder
Reflection based builder which saves you from a lot of typing. It will make you quick - less code to change when your models change. Of course, there is a performance impact because of the reflection, but since class definition building usually takes place during application startup, we hope it's not a problem for you.

######Getting Started
It's easy! Start by instantiating the builder:
```
ReflectionClassDefinitionBuilder builder = new ReflectionClassDefinitionBuilder();
```

Then pass in the class you want to create a definition for:
```
ClassDefinition classDefinition = builder.build(PortableClass.class);
```

Finally, add the definition to the serialization config
```
Config config = new Config();
config.getSerializationConfig().addClassDefinition(classDefinition);
```
