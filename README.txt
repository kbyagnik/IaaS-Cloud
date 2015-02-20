==================
IAAS Cloud Service
==================

By Kaushal Yagnik and Gaurav Mittal
-----------------------------------

-------------------
Problem Description
-------------------

To build a private IaaS (Infrastructure as a Service) using VirtualBox as the hypervisor. On this cloud, users should be able to request (through a web portal) the VMs of arbitrary configuration.

----------------
Software Summary
----------------

The system is essentially a web interface for the client build using JSP and Java Servlets, which enables the user/client to remotely administer the virtual machines by providing facilities such starting, stopping, cloning, removing or creating an altogether new virtual machine. The web interface employs the object-oriented web service (OOWS) provided by VirtualBox to achieve the objective.

--------
Contents
--------

1. src folder - It contains the project folder named VBoxSrv

2. VBoxSrv.war - .war file to be placed in tomcat webapps folder to run the application

3. README.txt - explaining the purpose of the software, steps to compile, install and run the program alongwith the user manual

------------
Requirements
------------

* VirtualBox 4.3.20 SDK
* Java 1.7 and above
* VirtualBox 4.3.20
* Apache-Tomcat 7
* Javascript enable Web Browser

----------
How To Run
----------

1. Make sure apache-tomcat 7 and all its dependencies are properly installed in the system hosting the application.

2. Since, by default, the application supports VirtualBox implementation on the localhost, start VBoxWebSrv service through the terminal on the same machine.

$ vboxwebsrv -v

3. Place the VBoxSrv.war file in the webapps folder of the apache-tomcat directory. For ubuntu, place the .war file in /var/lib/tomcat7/webapps

4. Restart the apache-tomcat server.

$ sudo /etc/init.d/tomcat7 restart

5. Open the Web Browser and go to http://localhost:8080/VBoxSrv.

6. If everything goes well, the web application will be loaded showing the list of VMs on the system.

--------
Features
--------

- On starting the web application, the user can see the list of all the virtual machine present on the system along with their current state such as 'Running', 'PoweredOff', 'Aborted' or anything else.

- The user can select a virtual machine from this radio buttoned list and perform any given action on that specific virtual machine.

- Following are the actions user can perform on an virtual machine after selecting it:
	
	+ Start : A virtual machine which is in 'PoweredOff' or 'Aborted' state can be started by this action. Successful start of the machine is indicated by a change in the state of the VM to 'Running'.
	
	+ Stop : A virtal machine which is in 'Running' state can be stopped by this action. Successful stop of the machine is indicated by a change in the state of the VM to 'PoweredOff'.
	
	+ Clone : The selected VM if in 'PoweredOff' state can be cloned to create another VM with identical specification and optionally with copy of disks and other media. User have to specify through a form, the name of the new cloned VM to be created, the mode of cloning - Full or Linked and option of whether MAC addresses of the new VM need to reinitialized.
	
	+ Delete : The selected VM if in 'PoweredOff' state can be completely removed from the system along with its disk using this action. Successful removal of the VM is reflected by it not getting listed anymore.
	
	+ Manage : The paramters such as CPU count and RAM size of the selected VM can be changed if it is in 'PoweredOff' state. If it is in 'Running' state, its various performance metrics can be viewed.
	
- Apart from performing any particular action on an individual already existing VM, user has the option to create a new VM in two different ways:

	+ Create From Existing : User can select from a list of pre-created VM which will then be cloned to create the new Virtual Machine.
	
	+ Create New : User can select various parameter like CPU Count and RAM size and create a completely new VM. Apart from this, the user can create a new virtual disk in various available formats to be attached to the new VM.
	

