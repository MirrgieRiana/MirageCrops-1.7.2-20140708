
use strict;
use File::Basename;
use JSON;
use Text::CSV_XS;

main();

sub main {
	my $table = readCsvFile( dirname(__FILE__) . "/" . "data.csv" );

	my $json = to_json($table);
	print $json;
	writeFile( dirname(__FILE__) . "/" . "data.json", $json );
}

sub readCsvFile( $ ) {
	my $filename = shift;
	my $buf      = readFile($filename);

	my $csv = Text::CSV_XS->new();

	my $table = [ split /\n/, $buf ];
	map {
		$csv->parse($_);
		$_ = [ $csv->fields ];
	} @$table;

	my $cols = max( map { $#$_ } @$table ) + 1;
	map {
		for my $c ( 0 .. $cols - 1 )
		{
			if ( !defined $_->[$c] ) {
				$_->[$c] = "";
			}
		}
	} @$table;

	return $table;
}

sub readFile( $ ) {
	my $filename = shift;
	open FP, "<", $filename;
	my $buf = join "", <FP>;
	close FP;
	return $buf;
}

sub writeFile( $$ ) {
	my $filename = shift;
	my $buf      = shift;

	open FP, ">", $filename;
	print FP $buf;
	close FP;
}

sub max( @ ) {
	my $t = shift;
	for (@_) {
		$t = $_ if $t < $_;
	}
	return $t;
}

