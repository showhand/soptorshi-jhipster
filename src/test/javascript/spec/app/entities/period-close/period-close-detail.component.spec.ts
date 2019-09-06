/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PeriodCloseDetailComponent } from 'app/entities/period-close/period-close-detail.component';
import { PeriodClose } from 'app/shared/model/period-close.model';

describe('Component Tests', () => {
    describe('PeriodClose Management Detail Component', () => {
        let comp: PeriodCloseDetailComponent;
        let fixture: ComponentFixture<PeriodCloseDetailComponent>;
        const route = ({ data: of({ periodClose: new PeriodClose(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PeriodCloseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PeriodCloseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PeriodCloseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.periodClose).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
