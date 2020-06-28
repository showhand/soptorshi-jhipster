/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyAreaWiseAccumulationDetailComponent } from 'app/entities/supply-area-wise-accumulation/supply-area-wise-accumulation-detail.component';
import { SupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';

describe('Component Tests', () => {
    describe('SupplyAreaWiseAccumulation Management Detail Component', () => {
        let comp: SupplyAreaWiseAccumulationDetailComponent;
        let fixture: ComponentFixture<SupplyAreaWiseAccumulationDetailComponent>;
        const route = ({ data: of({ supplyAreaWiseAccumulation: new SupplyAreaWiseAccumulation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyAreaWiseAccumulationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyAreaWiseAccumulationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyAreaWiseAccumulationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyAreaWiseAccumulation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
