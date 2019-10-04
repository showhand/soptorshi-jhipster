import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PeriodCloseComponent,
    PeriodCloseDetailComponent,
    PeriodCloseUpdateComponent,
    PeriodCloseDeletePopupComponent,
    PeriodCloseDeleteDialogComponent,
    periodCloseRoute,
    periodClosePopupRoute
} from './';

const ENTITY_STATES = [...periodCloseRoute, ...periodClosePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*    declarations: [
        PeriodCloseComponent,
        PeriodCloseDetailComponent,
        PeriodCloseUpdateComponent,
        PeriodCloseDeleteDialogComponent,
        PeriodCloseDeletePopupComponent
    ],
    entryComponents: [PeriodCloseComponent, PeriodCloseUpdateComponent, PeriodCloseDeleteDialogComponent, PeriodCloseDeletePopupComponent],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPeriodCloseModule {}
