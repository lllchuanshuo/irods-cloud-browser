package org.irods.jargon.idrop.web.controllers

import grails.converters.JSON
import grails.rest.RestfulController

import org.irods.jargon.idrop.web.services.UserService

class UserSearchController extends RestfulController  {

	UserService userService
	static responseFormats = ['json']

	/**
	 * Obtain a listing of users via a search, with the user name as the userName parameter, group=true will search groups
	 * 
	 * @return
	 */
	def show() {
		log.info("show")
		def irodsAccount = request.irodsAccount
		if (!irodsAccount) throw new IllegalStateException("no irodsAccount in request")

		def userName = params.userName
		if (!userName) userName = ""

		log.info("userName:${userName}")

		def group = params.group
		if (!group) group = false;

		log.info("group?:${group}")


		if (group) {
			log.info("looking up groups")
			def userList = userService.listUserGroups(userName, irodsAccount)
			log.info("userList:${userList}")
			render userList as JSON
		} else {
			log.info("looking up users")
			def userList = userService.listUsers(userName, irodsAccount)
			log.info("userList:${userList}")
			render userList as JSON
		}
	}
}