package com.dslplatform.examples

case class Row(columns: String*)

case class Table(headers: Row, rows: Seq[Row]) {
  override def toString = {
    val sb = new StringBuilder

    val maxLengths =
      (headers +: rows).map {
        _.columns map { _.length }
      }.transpose map { _.max }

    val nl = sys.props("line.separator")
    maxLengths foreach { len => sb ++= "+--" ++= "-" * len }
    sb += '+' ++= nl

    val border = sb.toString
    val separator = border.replace('-', '=')

    def appendRow(row: Row) = {
      sb ++= "| "
      (maxLengths zip row.columns) foreach { case (len, cell) =>
        val padding = len - cell.length
        sb ++= " " * (padding / 2) ++= cell ++= " " * (padding - padding / 2) ++= " | "
      }
      sb.length -= 1
      sb ++= nl
    }

    appendRow(headers)
    sb ++= separator

    rows foreach { row =>
      appendRow(row)
      sb ++= border
    }

    sb.toString
  }
}
