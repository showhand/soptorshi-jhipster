import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    WeekendDeleteDialogExtendedComponent,
    WeekendDeletePopupExtendedComponent,
    WeekendDetailExtendedComponent,
    WeekendExtendedComponent,
    weekendExtendedRoute,
    weekendPopupExtendedRoute,
    WeekendUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...weekendExtendedRoute, ...weekendPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WeekendExtendedComponent,
        WeekendDetailExtendedComponent,
        WeekendUpdateExtendedComponent,
        WeekendDeleteDialogExtendedComponent,
        WeekendDeletePopupExtendedComponent
    ],
    entryComponents: [
        WeekendExtendedComponent,
        WeekendUpdateExtendedComponent,
        WeekendDeleteDialogExtendedComponent,
        WeekendDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiWeekendExtendedModule {}
