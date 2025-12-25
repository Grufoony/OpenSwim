use csv::Reader;
use std::error::Error;
use std::fs::File;
use std::path::Path;

pub struct CsvImporter;

impl CsvImporter {
    pub fn new() -> Self {
        Self
    }

    /// Import CSV file and read its contents
    /// Returns the number of records read
    pub fn import_csv<P: AsRef<Path>>(&self, file_path: P) -> Result<usize, Box<dyn Error>> {
        let file = File::open(&file_path)?;
        let mut reader = Reader::from_reader(file);
        
        let mut record_count = 0;
        
        // Read headers
        let headers = reader.headers()?.clone();
        println!("CSV Headers: {:?}", headers);
        
        // Read each record
        for result in reader.records() {
            let record = result?;
            record_count += 1;
            
            // Print first few records for debugging
            if record_count <= 5 {
                println!("Record {}: {:?}", record_count, record);
            }
        }
        
        println!("Total records read: {}", record_count);
        Ok(record_count)
    }
}

impl Default for CsvImporter {
    fn default() -> Self {
        Self::new()
    }
}
