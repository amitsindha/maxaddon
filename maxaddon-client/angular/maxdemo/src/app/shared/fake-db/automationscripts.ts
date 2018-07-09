export class AutomationscriptsDB {
  public automationscripts = [
    {
      '_id': 1,
      'name': 'START',
      'description': 'Cluster or JVM Start script',
      'isCustom':false
    },
    {
      '_id': 2,
      'name': 'STOP',
      'description': 'Cluster or JVM Stop script',
      'isCustom':false 
    },
    {
      '_id': 3,
      'name': 'RESTART',
      'description': 'Cluster or JVM Restart script',
      'isCustom':false 
    },
    {
      '_id': 4,
      'name': 'BUILD',
      'description': 'Application Build script',
      'isCustom':false 
    },
    {
      '_id': 5,
      'name': 'DEPLOY',
      'description': 'Application Deployment script',
      'isCustom':false 
    },
    {
      '_id': 6,
      'name': 'BACKUP',
      'description': 'Server Backup script',
      'isCustom':true 
    },
    {
      '_id': 7,
      'name': 'RESTORE',
      'description': 'Server Restore script',
      'isCustom':true 
    }
  ]
}    