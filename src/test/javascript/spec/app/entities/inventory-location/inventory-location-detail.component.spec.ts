/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { InventoryLocationDetailComponent } from 'app/entities/inventory-location/inventory-location-detail.component';
import { InventoryLocation } from 'app/shared/model/inventory-location.model';

describe('Component Tests', () => {
    describe('InventoryLocation Management Detail Component', () => {
        let comp: InventoryLocationDetailComponent;
        let fixture: ComponentFixture<InventoryLocationDetailComponent>;
        const route = ({ data: of({ inventoryLocation: new InventoryLocation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [InventoryLocationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InventoryLocationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InventoryLocationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.inventoryLocation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
