## First view

This is an Java app which simulates an ecosystem with different animals.

## Getting started

To launch the app enter the command bellow:

```bash
$ java -Djava.library.path=lib/ -jar Game.jar [options]
```

The options:

| Option                  | Description                                     |
|:----------------------- |:----------------------------------------------- |
| --debug                 | To have more log                                |
| --fullScreen            | Start the game in full screen mod               |
| --size `width` `height` | Start the game with resolution `width`x`height` |

## How to play

| Shortcut | Action        |
|:-------- |:------------- |
| `Esc`    | Quit the game |

## Add some features

You can add some new animals just by editing the xml file `data/livingBeings.xml`.
Simply copy the code below, adapt and paste it to the end of the file.

```xml
<livingBeing>
    <id>21</id>
    <name>Rabbit</name>
    <valEnergy>40</valEnergy>

    <!-- Life -->
    <life>10</life>
    <lifeLimit>5</lifeLimit>

    <!-- Energy -->
    <energy>200</energy>
    <energyLimit>75</energyLimit>

    <!-- Interface -->
    <movement>Pedestrian</movement>
    <reproduction>Reproducible</reproduction>

    <!-- Food -->
    <food>
        <id>111</id>
    </food>
</livingBeing>
```

Tag description:

| Tag            | Description                                                                               |
|:-------------- |:----------------------------------------------------------------------------------------- |
| _id_           | Id of the living being                                                                    |
| _name_         | The name of the living beings for log. Is the path for sprite `data/img/:name:/sprite.png`|
| _valEnergy_    | The energetics value win when you eat it                                                  |
| _life_         | His maximum life                                                                          |
| _lifeLimit_    | Bellow this it became injured                                                             |
| _energy_       | His maximum energy                                                                        |
| _energyLimit_  | Bellow this his instinct is `nutritional`                                                 |
| _movement_     | His interface for him movement (Pedestrian or Fixed)                                      |
| _reproduction_ | His interface for him reproduction (Reproducible or Unreproducible)                       |
| _food_         | Its diet                                                                                  |

## Advancement

- v0.2.3: Balance the world
    * [x] _Fix some issues_
    + [x] Add interface `IReproduction` (now **Carrot** can't reproduce')
    + [ ] Balance the world

- v0.2.2: Options
    * _Fix some issues_
    - Migration class `Log` to `org.newdawn.slick.util.Log`
    + Add sound player with slick
    + Add some option to main class (debug, fullScreen, size)
    + Add auto-adjustment between **screen** size and **map** size

- v0.2.1: Mapping(v2)
    * _Fix some issues_
    - Remove old path finding because of issue
    - Remove old matrix map
    + Add new path finding (A* algorithm)
    + Add slick map
    + Add simple map generation (with grass and tree)

- v0.2: Graphic interface
    * _Fix some issues_
    - Migration to lib `Slick2D` (http://slick.ninjacave.com/)
    + Add class `Game` (main class)
    + Add sprite in living beings

- v0.1.2: Mapping
    * _Fix some issue_
    + Add map (matrix of tile)
    + Add **path finding**

- v0.1.1: More features
    * _Fix some issues_
    - Remove class `Rabbit` and `Carrot`
    + Add XML data file `data/livingBeings.xml` (lib: `jDOM-2.0.6` http://www.jdom.org/)
    + Add better behavior

- v0.1: First code
    + Add class `LivingBeing`
    + Add pattern state (Alive, Injured, Dead)
    + Add pattern state instinct (Reproduction or Nutritional)
    + Add pattern strategy for deplacement (Pedestrian or Fixed)
    + Add class `Rabbit` and `Carrot` (extends LivingBeing)
    + Add world

- v0: Start