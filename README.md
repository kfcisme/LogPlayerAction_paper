# Log Player Action 玩家行為紀錄系統

---

## 專案簡介
一個用於 **Minecraft Paper 伺服器** 的玩家行為紀錄插件。  
本專案會即時監測玩家在遊戲中的各種行為事件，並將資料整理後寫入資料庫。  
>此系統用於2025及2026台灣國際科展用途  
> 若使用到此次科展相關系統，**請務必安裝並使用此插件**

---

## 功能

1) 此插件共紀錄27種玩家行為 包含  
`block_place` `block_break` `interact` `block_damage` `dmg_by_entity` `player_death` `death` `exp_change` `level_change` `item_pickup` `item_drop` `craft` `furnace_extract` `inv_open` `inv_close` `cmd_pre` `cmd_send` `chunkload` `teleport` `bucket_fill` `bucket_empty` `tnt_prime` `explosion` `redstone` `chat`  
**若想啟用 `AFK` 請務必安裝 Essentials 插件**
  

2) 資料庫相關  
此專案僅支援 mySQL 與 SQLite 兩種資料庫，並且使用 HikariCP 進行連線池管理。  
每位玩家皆為獨立計時器，且每次寫入都是以UUID為主鍵，若玩家在同一時間窗內有多次行為事件，則會將事件數量累加後寫入資料庫。

---

## 專案結構

```
me/wowkfccc/logplayeraction/
├── listeners/
│   ├── plugin/
│   │   ├── onEssentialsAFK.java                
│   ├── BlockListener.java
│   ├── EntityListener.java
│   ├── InventoryListener.java
│   ├── PlayerListener.java
│   └── ...
├── LogPlayerAction.java        
├── mySQLConnect.java
├── mySQLinsertData.java
├── PlatformScheduler.java
├── commandmanager.java 
```

---

## 執行需求

- Java 17+
- Paper 1.20.4+
- MySQL
- EssentialsX（用於 AFK ）

---

