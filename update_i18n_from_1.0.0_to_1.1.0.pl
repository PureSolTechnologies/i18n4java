#!/usr/bin/perl -w

foreach $i18nFile (`find $ARGV[0] -name '*.i18n'`)
{
    chomp($i18nFile);
    print "Process $i18nFile...\n";
    $text = "";
    foreach $line (`cat $i18nFile`)
    {
	$line =~ s/<translated>/<translations>/g;	
	$line =~ s/<\/translated>/<\/translations>/g;
	$text .= $line;
    }
    print $text;
    open FILE, ">$i18nFile";
    print FILE $text;
    close FILE;
}
