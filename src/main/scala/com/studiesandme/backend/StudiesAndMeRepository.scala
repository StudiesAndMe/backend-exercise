package com.studiesandme.backend

import java.sql.Timestamp
import java.time.Instant

import com.leoilab.db.{Clients, DBComponent, StandardMappers}
import com.leoilab.id.ClientId
import com.leoilab.newtype.NewtypeSlick
import com.studiesandme.backend.tasks.TaskId

trait StudiesAndMeColumnTypes extends NewtypeSlick { this: DBComponent =>
  implicit lazy val taskIdColumnType = deriveUUIDAsStringColumn(TaskId.apply)
}

trait StudiesAndMeRepository {
  implicit val clientId: ClientId = Clients.StudiesAndMe
}

trait StudiesAndMeMappers extends StandardMappers {
  this: DBComponent =>

  import slick.jdbc.MySQLProfile.api._
  implicit val instantColumnType: ColumnType[Instant] =
    MappedColumnType.base[Instant, Timestamp](
      { instant =>
        Timestamp.from(instant)
      }, { timestamp =>
        Instant.ofEpochMilli(timestamp.getTime())
      },
    )
}
