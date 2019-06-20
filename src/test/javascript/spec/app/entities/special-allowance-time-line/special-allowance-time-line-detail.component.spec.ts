/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SpecialAllowanceTimeLineDetailComponent } from 'app/entities/special-allowance-time-line/special-allowance-time-line-detail.component';
import { SpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';

describe('Component Tests', () => {
    describe('SpecialAllowanceTimeLine Management Detail Component', () => {
        let comp: SpecialAllowanceTimeLineDetailComponent;
        let fixture: ComponentFixture<SpecialAllowanceTimeLineDetailComponent>;
        const route = ({ data: of({ specialAllowanceTimeLine: new SpecialAllowanceTimeLine(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SpecialAllowanceTimeLineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SpecialAllowanceTimeLineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpecialAllowanceTimeLineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.specialAllowanceTimeLine).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
