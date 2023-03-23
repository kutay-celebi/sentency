import dayjs from "dayjs";

export const useDateUtility = () => {
  const dateLongFormat = (val: Date) => {
    return dayjs(val).format("YYYY/MM/DD HH:mm");
  };

  return {
    dateLongFormat,
  };
};
