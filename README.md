# SapiApExtension

This project aimed to add some components for SkillAPI to interact with AttributePlus.
 
## Component
 * [Took AP Damage](#took-ap-damage)
 * [Took Entity Damage](#took-entity-damage)
 * [Value AttributePlus](#value-attributeplus)
 * [Append AttributePlus](#append-attributeplus)
 * [Remove AttributePlus](#remove-attributeplus)

## Details
Notice: Chinese only

### Took AP Damage
**Type: Trigger**

该组件依赖于 AttributePlus 中的 AttrEntityDamageEvent，通过对该事件的监听完成对 AP 伤害的操作。  
由于 AttributePlus 对于真实伤害的设计问题，该组件无法监听 AttributePlus 中的真实伤害。

* 参数列表
  
  |关键字|名称|类型|描述|
  |:---:|:---:|:---:|:---|
  |target|Target|Dropdown\[True, False\]|选择标记技能使用者(True)或伤害来源(False)|
  |limit-min|Limit Min|Dropdown\[True, False]|是否限制下限|
  |dmg-min|Min Damage|Number(0,0)|触发器响应的伤害下限|
  |limit-max|Limit Max|Dropdown\[True, False]|是否限制上限|
  |dmg-max|Max Damage|Number(999,0)|触发器响应的伤害上限|
* 记录值
  
  |关键字|描述|
  |:---:|:---|
  |api-dealt|使该触发器响应的伤害值|

### Took Entity Damage
**Type: Trigger**

AttributePlus 的伤害处理执行在 Bukkit 事件线的 [HIGHEST][1] 等级上，而 SkillAPI 的 Trigger 对事件的监听建立在 [HIGH][2] 等级上，因此，无法在同一个事件上完成先被 AttributePlus 处理，后被 SkillAPI 监听。

该组件通过外部监听器监听 Bukkit 中的 [EntityDamagedByEntityEvent][3]，包装该事件形成 BridgeDamageEntityEvent 后由 Trigger 监听 BridgeDamageEntityEvent 实现对伤害的操作。  

在此组件响应时，原本事件的伤害值已被 AP 修改，可以认为此组件记录的伤害值即为 AP 插件造成的伤害。

* 参数列表：同 [ApDamageListener](#took-ap-damage)
* 记录值：同 [ApDamageListener](#took-ap-damage)

特殊的，这个组件并不依赖AttributePlus的任何代码，尽管如此，这个组件仍被列在这里，是因为这个组件是为了实现监听 AttributePlus 中全部伤害（包括真实伤害）而设计的。在 AttributePlus 做出相关更改前可以使用此组件来近似实现。

### Value AttributePlus
**Type: Mechanic**

该组件通过 AttributeAPI 完成对 AttributePlus 属性的读取。  
* 参数列表
  
  |关键字|名称|类型|描述|
  |:---:|:---:|:---:|:---|
  |attrName|AttrName|Text( )|要记录的属性名，**不要**添加"\[0\]"或"\[1\]"后缀|
  |min_max|Min_Max|Dropdown\[min, max\]|记录最小值或记录最大值|
  |random|Random|Dropdown\[true, false\]|是否生成随机值|
  |key|Key|Text(key)|储存读取值的关键字|
* 参数处理规则:
  * 在attrName所代表的属性可接受随机值时，若random设置为true，返回介于最小值与最大值之间的随机值，若random设置为false，返回min_max所要求的值。
  * 在attrName所代表的属性不可接受随机值时，无论min_max与random如何设置，总是返回该属性的固定值。

### Append AttributePlus
**type: Mechanic**

该组件通过 AttributeAPI 完成对 AttributePlus 属性的增减。
* 参数列表

  |关键字|名称|类型|描述|
  |:---:|:---:|:---:|:---|
  |source|Source|Text(skill)|属性来源|
  |attr|Attr|Text()|属性名称|
  |value|Value|Number(0,0)|要增减的属性值|
* 参数处理规则:
  * source 冲突时，仅最后一次执行的属性会生效，若希望多个属性值生效，请使用多个不同的 source
  * 添加的属性值不会自动失效，请注意使用 [Remove AttributePlus](#remove-attributeplus) 组件对添加的属性进行手动清理。
  
### Remove AttributePlus
**type: Mechanic**

该组件通过 AttributeAPI 完成对 AttributePlus 属性的移除。
* 参数列表
  
  |关键字|名称|类型|描述|
  |:---:|:---:|:---:|:---|
  |source|Source|Text(skill)|属性来源|


[1]: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/EventPriority.html#HIGHEST
[2]: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/EventPriority.html#HIGH
[3]: https://hub.spigotmc.org/nexus/service/local/repositories/snapshots/archive/org/bukkit/bukkit/1.12.2-R0.1-SNAPSHOT/bukkit-1.12.2-R0.1-20180712.012114-155-javadoc.jar/!/org/bukkit/event/entity/EntityDamageByEntityEvent.html