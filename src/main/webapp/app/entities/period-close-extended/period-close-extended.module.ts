import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PeriodCloseExtendedComponent,
    PeriodCloseExtendedDetailComponent,
    PeriodCloseExtendedUpdateComponent,
    periodCloseExtendedRoute,
    periodCloseExtendedPopupRoute
} from './';
import {
    PeriodCloseComponent,
    PeriodCloseDeleteDialogComponent,
    PeriodCloseDeletePopupComponent,
    PeriodCloseDetailComponent,
    PeriodCloseUpdateComponent
} from 'app/entities/period-close';

const ENTITY_STATES = [...periodCloseExtendedRoute, ...periodCloseExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PeriodCloseComponent,
        PeriodCloseDetailComponent,
        PeriodCloseUpdateComponent,
        PeriodCloseExtendedComponent,
        PeriodCloseExtendedDetailComponent,
        PeriodCloseExtendedUpdateComponent,
        PeriodCloseDeleteDialogComponent,
        PeriodCloseDeletePopupComponent
    ],
    entryComponents: [
        PeriodCloseExtendedComponent,
        PeriodCloseExtendedUpdateComponent,
        PeriodCloseDeleteDialogComponent,
        PeriodCloseDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPeriodCloseModule {}
