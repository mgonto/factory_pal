#FactoryPal

FactoryPal is a scala framework that lets you create objects as test data. All you have to do is define the templates for each of the classes that you want FactoryPal to create objects from. After that, FactoryPal takes care of the rest. 

Have you ever heard of factory_girl a super cool Ruby framework? Well, FactoryPal is pretty similar in its use. The difference is that FactoryPal is 100% type safe, which all of us Scala people love.

##How do we use this?

FactoryPal is a singleton object where you can register all of the templates. For example, you can define a template as follows:

````scala
FactoryPal.register[Person]() { person =>
    person.name.mapsTo("gonto") and
    person.age.isRandom
}
````

In this example, we register a new template for class model. If we try to set a value for a property that Person doesn't has, your project won't compile. If you try to set a value to a property that isn't the type of that property, the project won't compile either. Pretty cool huh? This was possible thanks to Scala Macros and Dynamic, two features added in the latest Scala 2.10 RC release.

For the time being, there are 3 supported operations on a field template.

* MapsTo: This sets a certain specific value to that property
* isRandom: This sets a random value based on the type of the field. I've created some implicit randomizers for common objects (String, Long, Int, Double, etc.) but you can create your own. This is pretty similar to Ordering[T] used in List
* isAnotherFactoryModel: You tell FactoryPal that this is an inner object that can be constructed with another template of FactoryPal. For the time being, there can only be one template for each class. I'm going to change this very soon.

After we created the template, we can instantiate objects of that template as follows:

````scala
val person = FactoryPal.create[Person]
````

The create method has another overload that lets you add some field overriders for certain test. For example you can do the following:

````scala
val person = FactoryPal.create[Person]() { person  =>
    person.age.mapsTo(45) alone
}
````

Note that in this example we've added an alone at the end. If your builder has only one property, you need to tell this to FactoryPal by adding the alone at the end.

And that's it. That's all you need to know to use this.

## Multiple templates for class

Now, you can have multiple templates for one class. How do you do so? Both register and create take an optional extra parameter that is an Option[Symbol]. That will be the name of your model

So, let's register a coolPerson

````scala
FactoryPal.register[Person](Some('coolPerson)) { person =>
    person.name.mapsTo("gonto") and
    person.age.isRandom
}
````

and let's use him :)

````scala
val person = FactoryPal.create[Person](Some('coolPerson))()
````

## How can I add this to my project?

This is an example configuration for Build.scala for your SBT project :). There're only snapshots for now as Scala 2.10 is not yet final. Once it's, I'm going to make a release. You can check this out in the sample in this project.
````scala
import sbt._
import sbt.Keys._

object ApplicationBuild extends Build {

  lazy val root = Project(
    id = "factory_pal_sample",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "factory_pal_sample",
      organization := "ar.com.gonto",
      version := "0.1",
      scalaVersion := "2.10.0-RC3",
      scalacOptions += "",
      licenses      := ("Apache2", new java.net.URL("http://www.apache.org/licenses/LICENSE-2.0.txt")) :: Nil,
      libraryDependencies ++= Seq(
       "org.scala-lang" % "scala-compiler" % "2.10.0-RC3",
       "ar.com.gonto" % "factory_pal_2.10" % "0.1.1-SNAPSHOT",
       "org.scalatest" % "scalatest_2.10.0-RC3" % "1.8-B1" % "test"
      ),
      resolvers ++= Seq(
         "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
         Resolver.url("Factory Pal Repository", 
          url("http://mgonto.github.com/snapshots/"))(Resolver.ivyStylePatterns)
      )
    )
  )
}
````

Note the FactoryPal dependency and the FactoryPal repository.

##What does it use internally?

Internally, this framework uses Scala Macros, Dynamic and the new Reflection library provided by Scala 2.10.

Besides this, every class, every object except for FactoryPal object are Inmutable. I think that this is the best practice to use. FactoryPal has a mutable field with an inmutable map assigned to it where I add the new templates added by the user

##Next Steps

The next things I want to do are:

* ~~Multiple templates for class~~
* Refactor Randomizer to extend Function to use this inline easily
* Add template inheritance
* Add helpers to use this with ScalaTest and Specs2. For the moment, you can create the templates in the before.


##Licence

FactoryPal is distributed under the [Apache 2 licence](http://www.apache.org/licenses/LICENSE-2.0.html)
