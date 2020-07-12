/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneWiseAccumulationDetailComponent } from 'app/entities/supply-zone-wise-accumulation/supply-zone-wise-accumulation-detail.component';
import { SupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';

describe('Component Tests', () => {
    describe('SupplyZoneWiseAccumulation Management Detail Component', () => {
        let comp: SupplyZoneWiseAccumulationDetailComponent;
        let fixture: ComponentFixture<SupplyZoneWiseAccumulationDetailComponent>;
        const route = ({ data: of({ supplyZoneWiseAccumulation: new SupplyZoneWiseAccumulation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneWiseAccumulationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyZoneWiseAccumulationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyZoneWiseAccumulationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyZoneWiseAccumulation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
