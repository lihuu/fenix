# ✈️ ActiveRecord 模式 :id=active-record-mode

在简单应用中，领域模型和数据库结构几乎是一致的，对应每个数据库表都有一个领域类。在这种情况下，可以让每个对象负责数据库的存取过程，这也就是 `Active Record`（活动记录）。

通常 Java 中的 ActiveRecord 模式都采用了“**继承**”的方式来操作实体对象，但考虑到 Java 只有单继承的因素。而实际工程中大多数实体类都可能继承了公共的基础实体类。所以，在 Fenix 中，实体类是通过组合**实现各个 Model 接口**来做“增删改查”的，灵活性更高。

## 🍣 一、选择 Model 接口 :id=choose-model

在集成了 Fenix 的 JPA 中，根据继承关系存在 `CrudRepository`、`PagingAndSortingRepository`、`JpaRepository` 和 `FenixJpaRepository`（Fenix 提供的）四个 Repository 接口。

在 Fenix 的 ActiveRecord 模式中，本质上仍然需要这几个 `Repository`，并基于此继承关系分别提供了四个 `Model` 接口，你须要按需选择对应的接口。对应关系如下：

- CrudRepository -> `CrudModel`
- PagingAndSortingRepository -> `PagingAndSortingModel`
- JpaRepository -> `JpaModel`
- FenixJpaRepository -> `FenixJpaModel`

## 🍡 二、实现接口 :id=impl-model

首先，在实体类中实现 Fenix 提供的 `JpaModel` 接口，在该接口中，你需要传递三个泛型参数，按顺序分别是**当前实体类**、**本实体类的 ID 类型**和**本实体类对应的 `Repository` 接口**。

```java
public class Blog implements JpaModel<Blog, String, BlogRepository> {

    @Id
    @Column(name = "c_id")
    private String id;

    ...

}
```

下面是实体类所对应的 `Repository` 接口，该接口继承了 `JpaRepository` 接口，它与 `JpaModel` 是关联对应的。

```java
@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {

}
```

## 🍕 三、增删改查 :id=do-curd

### 🍨 1. 插入或更新数据

```java
// 直接通过 Blog 对象保存或更新数据.
new Blog()
    .setTitle(TITLE)
    ...
    .save(); 

// 保存新数据或者更新属性不为 null 的字段（本方法是 Fenix 提供，需实现 FenixJpaModel）
blog.saveOrUpdateByNotNullProperties();
```

### 🍩 2. 删除数据

```java
// 根据已有实体删除.
blog.delete();

// 根据 ID 删除实体.
new Blog().setId().deleteById();
```

### 🎂 3. 根据 ID 查询数据

```java
// 根据 ID 查询实体数据.
new Blog().setId().findById();

// 根据 ID 查询实体是否存在.
blog.existsById();
```

### 🍰 4. 调用任意 Repository 中的方法

通过调用实体类中的 `getRepository()` 方法拿到对应实体的 `Repository` 对象，就可以调用 JPA 或你自己定义的任意方法。

```java
blog.getRepository().findXxx();
```