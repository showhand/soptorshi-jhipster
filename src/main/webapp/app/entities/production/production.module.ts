import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ProductionComponent,
    ProductionDeleteDialogComponent,
    ProductionDeletePopupComponent,
    ProductionDetailComponent,
    productionPopupRoute,
    productionRoute,
    ProductionUpdateComponent
} from './';

const ENTITY_STATES = [...productionRoute, ...productionPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductionComponent,
        ProductionDetailComponent,
        ProductionUpdateComponent,
        ProductionDeleteDialogComponent,
        ProductionDeletePopupComponent
    ],
    entryComponents: [ProductionComponent, ProductionUpdateComponent, ProductionDeleteDialogComponent, ProductionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiProductionModule {}
