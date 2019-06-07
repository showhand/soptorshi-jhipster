import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    FineComponent,
    FineDetailComponent,
    FineUpdateComponent,
    FineDeletePopupComponent,
    FineDeleteDialogComponent,
    fineRoute,
    finePopupRoute
} from './';

const ENTITY_STATES = [...fineRoute, ...finePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [FineComponent, FineDetailComponent, FineUpdateComponent, FineDeleteDialogComponent, FineDeletePopupComponent],
    entryComponents: [FineComponent, FineUpdateComponent, FineDeleteDialogComponent, FineDeletePopupComponent],
    exports: [FineComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiFineModule {}
