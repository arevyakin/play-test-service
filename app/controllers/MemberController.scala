package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.Member

object MemberController extends Controller {

  val memberForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "email" -> nonEmptyText
    )
  )

  val idForm = Form("id" -> longNumber)

  def memberToAdd = Action {
    Logger.info("Add member page")
    Ok(views.html.member_add(memberForm))
  }

  def addMember = Action { implicit request =>
    memberForm.bindFromRequest.fold(

      errors => BadRequest(views.html.member_add(errors)),

      x => x match {
        case (name, email) => {
          Member.add(name, email)
          Logger.info("Added member with name: " + name + ", email: " + email)
          Redirect(routes.ApplicationController.index)
        }
      })
  }

  def removeMember(id: Long) = Action {
    Member.delete(id)
    Logger.info("Removed member with id: " + id)
    Redirect(routes.ApplicationController.index)
  }

  def infoMember = Action {
    Logger.info("Info page")
    Ok(views.html.member_info(idForm))
  }

  def getMember = Action { implicit request =>
    idForm.bindFromRequest.fold(

      errors => BadRequest(views.html.member_info(errors)),

      x => x match {
        case id => {
          Logger.info("get member's information with id: " + id)
          Ok(views.html.members(Member.getMember(id)))
        }
      })
  }

}