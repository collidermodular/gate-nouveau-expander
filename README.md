# gate-nouveau-expander
Expander Library for Gate Nouveau 64

## VMD Project Setup

#### Option 1: Download Release JAR
1. Visit the releases page: https://github.com/collidermodular/gate-nouveau-expander/releases
1. Download latest release
1. Within Voltage Module Designer, click "Edit" next to "Additional JAR Files"
1. Click "+" and navigate to where the JAR was downloaded
1. Click "Ok"

#### Option 2: Compile from Source

1. Clone this repository
1. Open with IntelliJ
1. On the menu, choose Build > Build Artifacts > Build
1. Within Voltage Module Designer, click "Edit" next to "Additional JAR Files"
1. Click "+" and navigate to `../out/artifacts/gate-nouveau-expander/gate-nouveau-expander.jar`
1. Click "Ok"

## VMD Code Setup

1. Add to user imports:
```java
import com.collidermodular.gn.expander.GateNouveauExpander;
```

2. Add to `[User Variables and Functions]`:
```java
GateNouveauExpander expander;
```

3. Add to `Initialize()`:
```java
expander = new GateNouveauExpander();
```

4. Add to `ProcessSample()`:
```java
if (expander.hasNext()) {
  // NOTE: `trackIndex` here will need to be set by the module
  polyOutputJack1.SetPolyValue(trackIndex, expander.nextDouble());  
}
```

## Usage

### Creating Steps
```java
expander.setSimpleStep(0, true, 0.4); // index, enabled, gate width
expander.setAccentStep(0, 0.6); // index, gate width
expander.setTiedSteps(0, 4, 0.5); // start index, end index, end gate width
expander.setSubdividedSTep(0, 6, new boolean[] { true, false, true, true, true, false, true, false });
```

### Sending Commands
```java
expander.setTrackLength(60);
expander.setTrackDivision(4); // value is PPQN; 4 = 1/16th note
```