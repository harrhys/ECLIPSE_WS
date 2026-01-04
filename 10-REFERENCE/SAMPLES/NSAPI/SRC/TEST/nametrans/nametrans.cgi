#!/usr/bin/perl

print "Content-type:text/plain\n\n";


@arr = `env`;

foreach $line (@arr) 
{
   print $line;
}

