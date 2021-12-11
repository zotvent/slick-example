package kz.example.component.impl

import com.typesafe.config.{Config, ConfigFactory}
import kz.example.component.ConfigComponent

trait ConfigComponentImpl extends ConfigComponent {
  override val config: Config = ConfigFactory.load()
}
