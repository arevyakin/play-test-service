package controllers

import play.api._
import play.api.mvc._
import models.Member

object ApplicationController extends Controller {

  def start = Action {
    Logger.info("Start page")
    Ok(views.html.index("Your new application is ready."))
  }

  def index = Action {
    Logger.info("Index page")
    Ok(views.html.members(Member.all()))
  }

}