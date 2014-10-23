portable-util
=============
This repository contains a number of useful utilities that can help you with your Hazelcast development. We aim to design the utilities in such as way they will fit into any architecture regardless of the technology choice.

####ReflectionClassDefinitionBuilder
This is a reflection based class definition builder which saves you typing in your definitions by hand. It will make you faster - one less area to change when your models change. Of course, it's not super fast since we use reflection, but since class definition building usually takes place during application start-up, this we hope is not a problem for you.

######Getting Started
It's easy! Start by instantiating the builder:
```
  ReflectionClassDefinitionBuilder builder = new ReflectionClassDefinitionBuilder();
```

Then pass in the class you want to create a definition for:
```
ClassDefinition classDefinition = builder.build(PortableClass.class);
```
