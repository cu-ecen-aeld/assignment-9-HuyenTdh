SUMMARY = "Example of how to build an external Linux kernel module"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit module

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98lddmodules"
#INITSCRIPT_PARAMS = "defaults 98"
EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-HuyenTdh.git;protocol=ssh;branch=master"
PV = "1.0+git${SRCPV}"
SRCREV = "4980ee4da0524f00d85610667a822abb99aff832"
SRC_URI += "file://S98lddmodules"

S = "${WORKDIR}/git/misc-modules"
FILES:${PN} += "${base_bindir}/module_load ${base_bindir}/module_unload ${sysconfdir}/rc5.d/S98lddmodules"

#using module_install (built-in)
#do_install:append () {
#    install -d ${D}${base_bindir}
#    install -m 0755 ${S}/module_load ${D}${base_bindir}/module_load
#    install -m 0755 ${S}/module_unload ${D}${base_bindir}/module_unload
    
#    Install the init script
#    install -d ${D}${sysconfdir}/rc5.d
#    install -m 0755 ${WORKDIR}/S98lddmodules ${D}${sysconfdir}/rc5.d/S98lddmodules
#}

do_compile () {
    oe_runmake
}

do_install () {
    install -d ${D}${base_bindir}
    install -m 0755 ${S}/module_load ${D}${base_bindir}/module_load
    install -m 0755 ${S}/module_unload ${D}${base_bindir}/module_unload

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0755 ${S}/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/faulty.ko
    install -m 0755 ${S}/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/hello.ko
    
    #Install the init script
    install -d ${D}${sysconfdir}/rc5.d
    install -m 0755 ${WORKDIR}/S98lddmodules ${D}${sysconfdir}/rc5.d/S98lddmodules
}

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

#RPROVIDES:${PN} += "kernel-module-faulty"
