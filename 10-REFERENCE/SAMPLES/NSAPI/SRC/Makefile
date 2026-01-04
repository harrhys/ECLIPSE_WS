# Defines for example NSAPI programs running under SOLARIS

CC_CMD=cc -DNET_SSL -DSOLARIS -D_REENTRANT
LD_SHAREDCMD=ld -G

all:
prepare:


INCLUDEDIR=../../../include


EXAMPLE_OBJS = addlog.o auth.o ntrans.o otype.o pcheck.o service.o
CGIWATCH_OBJS = cookie.o cgiwatch.o
QOS_OBJS = qos.o

INCLUDE_FLAGS=-I$(INCLUDEDIR) -I$(INCLUDEDIR)/base -I$(INCLUDEDIR)/frame
COMMON_DEFS=-DMCC_HTTPD -DXP_UNIX -DSPAPI20


all: example.so cgiwatch.so qos.so


example.so: $(EXAMPLE_OBJS)
	$(MAKE) prepare
	$(LD_SHAREDCMD) $(EXAMPLE_OBJS) -o ../example.so $(EXTRA_LDDEFINES)

cgiwatch.so: $(CGIWATCH_OBJS)
	$(MAKE) prepare
	$(LD_SHAREDCMD) $(CGIWATCH_OBJS) -o ../cgiwatch.so $(EXTRA_LDDEFINES)

qos.so: $(QOS_OBJS)
	$(MAKE) prepare
	$(LD_SHAREDCMD) $(QOS_OBJS) -o ../qos.so $(EXTRA_LDDEFINES)

.c.o:
	$(CC_CMD) $(COMMON_DEFS) $(INCLUDE_FLAGS) -c $< 

clean:
	rm $(OBJS) example.so cgiwatch.so qos.so $(EXTRA_CLEAN)
