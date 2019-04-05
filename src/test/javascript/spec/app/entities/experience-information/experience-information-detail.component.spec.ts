/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ExperienceInformationDetailComponent } from 'app/entities/experience-information/experience-information-detail.component';
import { ExperienceInformation } from 'app/shared/model/experience-information.model';

describe('Component Tests', () => {
    describe('ExperienceInformation Management Detail Component', () => {
        let comp: ExperienceInformationDetailComponent;
        let fixture: ComponentFixture<ExperienceInformationDetailComponent>;
        const route = ({ data: of({ experienceInformation: new ExperienceInformation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ExperienceInformationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExperienceInformationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExperienceInformationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.experienceInformation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
