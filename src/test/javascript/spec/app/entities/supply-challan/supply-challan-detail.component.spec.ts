/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyChallanDetailComponent } from 'app/entities/supply-challan/supply-challan-detail.component';
import { SupplyChallan } from 'app/shared/model/supply-challan.model';

describe('Component Tests', () => {
    describe('SupplyChallan Management Detail Component', () => {
        let comp: SupplyChallanDetailComponent;
        let fixture: ComponentFixture<SupplyChallanDetailComponent>;
        const route = ({ data: of({ supplyChallan: new SupplyChallan(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyChallanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyChallanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyChallanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyChallan).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
