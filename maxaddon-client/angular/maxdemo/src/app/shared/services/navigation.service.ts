import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

interface IMenuItem {
  type: string,       // Possible values: link/dropDown/icon/separator/extLink
  name?: string,      // Used as display text for item and title for separator type
  state?: string,     // Router state
  icon?: string,      // Material icon name
  tooltip?: string,   // Tooltip text 
  disabled?: boolean, // If true, item will not be appeared in sidenav.
  sub?: IChildItem[], // Dropdown items
  badges?: IBadge[]
}
interface IChildItem {
  type?: string,
  name: string,       // Display text
  state?: string,     // Router state
  icon?: string,
  sub?: IChildItem[]
}

interface IBadge {
  color: string;      // primary/accent/warn/hex color codes(#fff000)
  value: string;      // Display text
}

@Injectable()
export class NavigationService {
  constructor() { }


  iconMenu: IMenuItem[] = [
    {
      name: 'DASHBOARD',
      type: 'link',
      tooltip: 'Dashboard',
      icon: 'dashboard',
      state: 'dashboard',
      badges: [{ color: 'accent', value: '100+' }],
    },
    {
      name: 'Members',
      type: 'link',
      tooltip: 'Members',
      icon: 'person',
      state: 'members'
    },    
    {
      name: 'Servers',
      type: 'link',
      tooltip: 'Servers',
      icon: 'computer', 
      state: 'servers'
    },    
    {
      name: 'Clusters',
      type: 'link',
      tooltip: 'Clusters',
      icon: 'device_hub', 
      state: 'clusters'
    },    
    {
      name: 'JVM',
      type: 'link',
      tooltip: 'JVM',
      icon: 'dns', 
      state: 'jvms'
    },    
    {
      name: 'Applications',
      type: 'link',
      tooltip: 'Applications',
      icon: 'apps', 
      state: 'applications'
    },    
    {
      name: 'Script Type',
      type: 'link',
      tooltip: 'Script Type',
      icon: 'nfc', 
      state: 'scripttypes'
    },    
    {
      name: 'Scripts',
      type: 'link',
      tooltip: 'Scripts',
      icon: 'settings', 
      state: 'scripts'
    },    
    {
      name: 'Actions',
      type: 'link',
      tooltip: 'Actions',
      icon: 'build', 
      state: 'actions'
    },
    {
      name: 'Scheduler',
      type: 'link',
      tooltip: 'Scheduler',
      icon: 'date_range',
      state: 'scheduler'
    }      
  ]

  // Icon menu TITLE at the very top of navigation.
  // This title will appear if any icon type item is present in menu.
  iconTypeMenuTitle: string = 'Frequently Accessed';
  // sets iconMenu as default;
  menuItems = new BehaviorSubject<IMenuItem[]>(this.iconMenu);
  // navigation component has subscribed to this Observable
  menuItems$ = this.menuItems.asObservable();

  // Customizer component uses this method to change menu.
  // You can remove this method and customizer component.
  // Or you can customize this method to supply different menu for
  // different user type.
  publishNavigationChange(menuType: string) {
    this.menuItems.next(this.iconMenu);
  }
}