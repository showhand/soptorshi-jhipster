import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    WeekendComponent,
    WeekendDeleteDialogComponent,
    WeekendDeletePopupComponent,
    WeekendDetailComponent,
    weekendPopupRoute,
    weekendRoute,
    WeekendUpdateComponent
} from './';

const ENTITY_STATES = [...weekendRoute, ...weekendPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WeekendComponent,
        WeekendDetailComponent,
        WeekendUpdateComponent,
        WeekendDeleteDialogComponent,
        WeekendDeletePopupComponent
    ],
    entryComponents: [WeekendComponent, WeekendUpdateComponent, WeekendDeleteDialogComponent, WeekendDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiWeekendModule {}
